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
        width: 800, height: 472, titleBarStyle: "hidden"
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

ipcMain.on("get-files", async (event, callback) => {
    console.log ("get file ...");

    let options = {
        absolute: true,
        ignore : ["node_modules"]
    };

    let getFiles = (pattern) => {
        return new Promise((ok, reject) => {
            glob(pattern, options, (err, files) => {
                if (files) ok(files);
                else reject(err);
            });
        });
    }

    let zip = await getFiles("**/*.zip");
    let msi = await getFiles("**/*.msi");
    let amp = await getFiles("**/*.amp");

    var files = [msi, zip, amp].reduce((a, i) => a.concat(i), []);
    console.log(files);
    event.sender.send("get-files-reply", files);
});