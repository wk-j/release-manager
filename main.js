const { app, BrowserWindow } = require("electron");
const path = require("path");
const url = require("url");
const publishRelease = require("publish-release");
const { ipcMain } = require("electron");
const glob = require("glob")

let win;

function createWindow() {
    win = new BrowserWindow({
        icon: path.join(__dirname, "/images/photo.ico"),
        width: 800, height: 572, titleBarStyle: "hidden"
    });

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

ipcMain.on("cancel", () => {
    app.exit(0);
});