#include "serialconnection.h"
using namespace std;

SerialConnection::SerialConnection(QObject *parent)
    : QObject{parent}
{

}

auto SerialConnection::enumeratePorts() -> QVector<serial::PortInfo> {
    auto ports = serial::list_ports();
    return QVector<serial::PortInfo>(ports.begin(), ports.end());
}

// INITIALIZATION //

bool SerialConnection::initialize(QString port) {
    for(auto &detectedPort : SerialConnection::enumeratePorts()) {
        if(detectedPort.port == port.toStdString()) {
            this->_serial = make_shared<serial::Serial>(port.toStdString(), MORTAR_SERIAL_BAUD);
            return true;
        }
    }
    return false;
}

bool SerialConnection::initialized() {
    return this->_serial != nullptr;
}

bool SerialConnection::isOpen() {
    return this->_serial->isOpen();
}

// ////////////// //
