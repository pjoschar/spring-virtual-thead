package com.javatechie.controller;

import com.javatechie.service.PlatformReportService;
import com.javatechie.service.ReportService;
import com.javatechie.service.VirtualReportService;
import jdk.jfr.Unsigned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PlatformReportService platformReportService;

    @Autowired
    private VirtualReportService virtualReportService;

    @PostMapping("/{region}")
    public ResponseEntity<String> generateReport(@PathVariable String region) {
        reportService.generateReportForRegion(region);
        return ResponseEntity.ok( "✅ report started for region: " + region);
    }

    @PostMapping("/platform/{region}")
    public ResponseEntity<String> generateReportPlatform(@PathVariable String region) {
        platformReportService.generateReportForRegion(region);
        return ResponseEntity.ok( "✅ Platform report started for region: " + region);
    }

    @PostMapping("/virtual/{region}")
    public ResponseEntity<String> generateReportVirtual(@PathVariable String region) {
        virtualReportService.generateReportForRegion(region);
        return ResponseEntity.ok( "✅ Virtual report started for region: " + region);
    }



}
