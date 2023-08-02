#include "mainwindow.h"
#include "./ui_mainwindow.h"
#include "settingsdialog.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    SettingsDialog settings(this);
    settings.exec();
}

MainWindow::~MainWindow()
{
    delete ui;
}

