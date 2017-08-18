const { app, BrowserWindow } = require("electron");
const path = require("path")
const url = require("url")

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