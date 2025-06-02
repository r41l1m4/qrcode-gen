package com.ironia.qrcodegen.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ironia.qrcodegen.dto.QrCodeGenResponse;
import com.ironia.qrcodegen.ports.StoragePort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class QrCodeGenService {

    private final StoragePort storage;

    public QrCodeGenService(StoragePort storage) {
        this.storage = storage;
    }

    public QrCodeGenResponse genAndUpQrCode(String text, int qrcode_size) throws WriterException, IOException {
        byte[] qrCodeByteArray = generate(text, qrcode_size);
        String fileName = String.format("%s_%dx%d", UUID.randomUUID(), qrcode_size, qrcode_size);
        String url = upload(qrCodeByteArray, fileName);

        return new QrCodeGenResponse(url);
    }

    private byte[] generate(String text, int qrcode_size) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, qrcode_size, qrcode_size);

        ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", fileOutputStream);

        return fileOutputStream.toByteArray();
    }

    private String upload(byte[] fileData, String fileName) {
        return storage.upload(fileData, fileName, "image/png");
    }
}