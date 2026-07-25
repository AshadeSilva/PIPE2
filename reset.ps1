# Local Reset Script
# Stops the Gradle daemon and force-deletes build directories to resolve file lock issues.

Write-Host "--- Starting Reset ---" -ForegroundColor Cyan

Write-Host "Stopping Gradle Daemon..."
./gradlew --stop

Write-Host "Removing build directories..."
$buildDirs = @("build", "androidApp/build", "shared/build")

foreach ($dir in $buildDirs) {
    if (Test-Path $dir) {
        Write-Host "Deleting $dir..."
        Remove-Item -Path $dir -Recurse -Force -ErrorAction SilentlyContinue
    }
}

Write-Host "--- SUCCESS: Project Reset ---" -ForegroundColor Green
