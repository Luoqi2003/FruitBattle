# 清理输出目录
if (Test-Path "bin") {
    try {
        Get-Process | Where-Object {$_.ProcessName -like "*java*"} | Stop-Process -Force -ErrorAction SilentlyContinue
        Start-Sleep -Seconds 1
        $tempEmpty = New-Item -ItemType Directory -Force -Path ".\empty"
        robocopy /MIR ".\empty" ".\bin"
        Remove-Item -Path ".\empty" -Force
        Remove-Item -Path "bin" -Recurse -Force
    }
    catch {
        Write-Host "Warning: Issues cleaning bin directory, but continuing..."
    }
}

# 创建必要的目录
New-Item -ItemType Directory -Force -Path "bin"
New-Item -ItemType Directory -Force -Path "data/PersonData" -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path "data/PersonData/Person" -ErrorAction SilentlyContinue

# 创建空的 personList.txt 文件（如果不存在）
if (-not (Test-Path "data/PersonData/personList.txt")) {
    New-Item -ItemType File -Force -Path "data/PersonData/personList.txt"
}

# 获取 JDK 路径（包含 JavaFX）
$JAVA_HOME = "C:\Program Files\Java\jdk1.8.0_202"

# 获取所有 Java 源文件并创建对应的输出目录
$sourceFiles = Get-ChildItem -Path "src" -Filter "*.java" -Recurse
foreach ($file in $sourceFiles) {
    $relativePath = $file.Directory.FullName.Replace($PWD.Path + "\src\", "")
    $outputPath = Join-Path "bin" $relativePath
    New-Item -ItemType Directory -Force -Path $outputPath | Out-Null
}

Write-Host "Found $($sourceFiles.Count) Java source files"

# 编译所有文件
Write-Host "Compiling Java files..."
$sourceList = $sourceFiles | ForEach-Object { $_.FullName }
& "$JAVA_HOME\bin\javac.exe" -encoding UTF-8 -d bin `
    -cp "$JAVA_HOME\jre\lib\ext\jfxrt.jar;src" `
    $sourceList

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed!"
    exit 1
}

# 复制资源文件
Write-Host "Copying resources..."
Get-ChildItem -Path "resources" -Recurse | ForEach-Object {
    if ($_.PSIsContainer) {
        $targetPath = $_.FullName.Replace("resources", "bin")
        New-Item -ItemType Directory -Force -Path $targetPath | Out-Null
    } else {
        $targetPath = $_.FullName.Replace("resources", "bin")
        Copy-Item -Path $_.FullName -Destination $targetPath -Force
    }
}

# 检查编译后的类文件
$classFiles = Get-ChildItem -Path "bin" -Filter "*.class" -Recurse
Write-Host "Compiled $($classFiles.Count) class files"

# 检查是否缺少任何必需的类
$sourceFiles | ForEach-Object {
    $classPath = $_.FullName.Replace(".java", ".class").Replace("src", "bin")
    if (-not (Test-Path $classPath)) {
        Write-Host "Warning: Missing compiled class for $($_.Name)"
    }
}

# 运行程序
if (Test-Path "bin/shuiguoxiaoxiaole/Main.class") {
    Write-Host "Running application..."
    & "$JAVA_HOME\bin\java.exe" -cp "$JAVA_HOME\jre\lib\ext\jfxrt.jar;bin" shuiguoxiaoxiaole.Main
} else {
    Write-Host "Error: Main class not found!"
    exit 1
}