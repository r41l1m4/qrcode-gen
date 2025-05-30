package com.ironia.qrcodegen.ports;

public interface StoragePort {
    String upload(byte[] fileData, String fileName, String contentType);
}
