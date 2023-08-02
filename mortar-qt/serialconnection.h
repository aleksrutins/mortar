#ifndef SERIALCONNECTION_H
#define SERIALCONNECTION_H

#include <memory>

#include <QObject>

#include <QVector>

#ifdef _WIN32
#include <windows.h>
#else
#include <unistd.h>
#endif

#include <serial/serial.h>

#define MORTAR_SERIAL_BAUD 19200

class SerialConnection : public QObject
{
    Q_OBJECT
    std::shared_ptr<serial::Serial> _serial = nullptr;
public:
    explicit SerialConnection(QObject *parent = nullptr);

    static auto enumeratePorts() -> QVector<serial::PortInfo>;

    bool initialize(QString port);
    bool initialized();
    bool isOpen();

    std::shared_ptr<serial::Serial> getSerial() {
        return this->_serial;
    }
signals:

};

#endif // SERIALCONNECTION_H
