# 清理输出目录
if (Test-Path "bin") {
    Remove-Item -Path "bin" -Recurse -Force
}
New-Item -ItemType Directory -Force -Path "bin"

# 编译所有 Java 文件
$sourceFiles = Get-ChildItem -Path "src" -Filter "*.java" -Recurse
$sourceList = $sourceFiles | ForEach-Object { $_.FullName }
& 'C:\Program Files\Java\jdk1.8.0_202\bin\javac.exe' -encoding UTF-8 -d bin -cp "src" $sourceList

# 复制资源文件
Get-ChildItem -Path "resources" -Recurse | ForEach-Object {
    if ($_.PSIsContainer) {
        $targetPath = $_.FullName.Replace("resources", "bin")
        New-Item -ItemType Directory -Force -Path $targetPath
    } else {
        $targetPath = $_.FullName.Replace("resources", "bin")
        Copy-Item -Path $_.FullName -Destination $targetPath -Force
    }
}

# 运行前检查编译是否成功
if (Test-Path "bin/shuiguoxiaoxiaole/Main.class") {
    # 运行
    & 'C:\Program Files\Java\jdk1.8.0_202\bin\java.exe' -cp bin shuiguoxiaoxiaole.Main
} else {
    Write-Host "Compilation failed. Please check error messages."
}