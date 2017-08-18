const { app, BrowserWindow } = require("electron");
const path = require("path");
const url = require("url");
const publishRelease = require("publish-release");
const { ipcMain } = require("electron");

let win;

function createWindow() {
    win = new BrowserWindow({ 
        icon: path.join(__dirname, "/images/photo.ico"),
        width: 800, height: 472 , titleBarStyle: "hidden"});

    win.loadURL(url.format({
        pathname: path.join(__dirname, "public/index.html"),
        protocol: "file",
        slashed: true
    }));

    win.on("closed", () => {
        win = null;
    });
}

app.on("ready", createWindow)
app.on("window-all-closed", () => {
    if (process.platform !== "darwin") app.quit()
});

app.on("activate", () => {
    if (win == null) createWindow();
});

// RENDERER
function publishToGithub(release, successCallback, errorCallback) {
    publishRelease({
        token: "",
        owner: "bcircle",
        tag: release.version,
        name: release.title + " " +  release.version,
        nodes: release.releateNote,
        draft: false,
        prerlease : false,
        reuseRelease: true,
        reuseDraftOnly: true,
        assetns : release.assets,
        target_commitish: "master"
    }, (err, release) => {
        if(err) errorCallback(err);
        else successCallback(release);
    });
}

ipcMain.on("cancel", () => {
    app.exit(0);
});

ipcMain.on("click", () => {
    console.log("do something");
});