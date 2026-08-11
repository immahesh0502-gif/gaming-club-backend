    package com.mahesh.gamingclubmanagementsystem.controller;

    import com.mahesh.gamingclubmanagementsystem.entity.BusinessDay;
    import com.mahesh.gamingclubmanagementsystem.services.BusinessDayService;
    import org.springframework.web.bind.annotation.*;
    import com.mahesh.gamingclubmanagementsystem.dto.BusinessDayCloseResponse;

    @RestController
    @RequestMapping("/business-day")
    @CrossOrigin(origins = "*")
    public class BusinessDayController {

        private final BusinessDayService businessDayService;

        public BusinessDayController(BusinessDayService businessDayService) {
            this.businessDayService = businessDayService;
        }

        @GetMapping("/current")
        public BusinessDay getCurrentBusinessDay() {
            return businessDayService.getCurrentBusinessDay();
        }

        @PostMapping("/close")
        public BusinessDayCloseResponse closeBusinessDay() {

            return businessDayService.closeBusinessDay();

        }
        @PostMapping("/open")
        public BusinessDay openBusinessDay() {
            return businessDayService.openBusinessDay();
        }

    }