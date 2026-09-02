# simply run with ./check-pipeline.ps1
# Local Pipeline Check Script
# This mimics the steps run in the GitHub Actions CI/CD pipeline

$env:APK_PATH="androidApp/build/outputs/apk/release/androidApp-release.apk"

Write-Host "--- Starting Stage: COMPILE ---" -ForegroundColor Cyan
./gradlew :androidApp:assembleRelease

if ($LASTEXITCODE -ne 0) { Write-Error "Compilation Failed"; exit $LASTEXITCODE }

Write-Host "--- Starting Stage: COMPILE iOS ---" -ForegroundColor Cyan
./gradlew :shared:compileKotlinIosSimulatorArm64

if ($LASTEXITCODE -ne 0) { Write-Error "iOS Compilation Failed"; exit $LASTEXITCODE }

Write-Host "--- Starting Stage: TEST ---" -ForegroundColor Cyan
./gradlew :shared:testAndroidHostTest

if ($LASTEXITCODE -ne 0) { Write-Error "Tests Failed"; exit $LASTEXITCODE }

Write-Host "--- SUCCESS: Pipeline Check Passed Locally ---" -ForegroundColor Green
