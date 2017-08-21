var electronInstaller = require('electron-winstaller');

resultPromise = electronInstaller.createWindowsInstaller({
    appDirectory: 'builds/RMG-win32-x64',
    outputDirectory: 'release',
    authors: 'wk-j',
    exe: 'RMG.exe'
  });

resultPromise.then(() => console.log("It worked!"), (e) => console.log(`No dice: ${e.message}`));