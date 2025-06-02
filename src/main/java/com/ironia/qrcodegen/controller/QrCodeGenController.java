package com.ironia.qrcodegen.controller;

import com.google.zxing.WriterException;
import com.ironia.qrcodegen.dto.QrCodeGenRequest;
import com.ironia.qrcodegen.dto.QrCodeGenResponse;
import com.ironia.qrcodegen.service.QrCodeGenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/gen-qrcode")
public class QrCodeGenController {

    private final QrCodeGenService qrCodeGenService;

    public QrCodeGenController(QrCodeGenService genService) {
        this.qrCodeGenService = genService;
    }

    @PostMapping
    public ResponseEntity<QrCodeGenResponse> generate(@RequestBody QrCodeGenRequest toBeTransformed) {
        try {
            QrCodeGenResponse response = qrCodeGenService.genAndUpQrCode(toBeTransformed.data(), toBeTransformed.qrcode_size());
            return ResponseEntity.ok(response);
        } catch (WriterException we) {
            System.out.println(we.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}