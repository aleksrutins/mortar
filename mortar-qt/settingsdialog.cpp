#include "settingsdialog.h"
#include "serialconnection.h"
#include "ui_settingsdialog.h"

SettingsDialog::SettingsDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::SettingsDialog)
{
    ui->setupUi(this);

    setWindowTitle("Settings");

    auto ports = SerialConnection::enumeratePorts();
    for(auto &portInfo : ports) {
        ui->portSelection->addItem(QString(portInfo.port.c_str()));
    }
}

SettingsDialog::~SettingsDialog()
{
    delete ui;
}
