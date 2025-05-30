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

    public QrCodeGenResponse genAndUpQrCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", fileOutputStream);

        String url = storage.upload(fileOutputStream.toByteArray(), UUID.randomUUID().toString(), "image/png");

        return new QrCodeGenResponse(url);
    }
}
