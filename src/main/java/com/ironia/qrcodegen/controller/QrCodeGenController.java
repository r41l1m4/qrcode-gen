package com.ironia.qrcodegen.controller;

import com.ironia.qrcodegen.dto.QrCodeGenRequest;
import com.ironia.qrcodegen.dto.QrCodeGenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gen-qrcode")
public class QrCodeGenController {

    @PostMapping
    public ResponseEntity<QrCodeGenResponse> generate(@RequestBody QrCodeGenRequest toBeTransformed) {
        return null;
    }
}