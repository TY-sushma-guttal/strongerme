package com.tyss.strongameapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.dto.PaymentRequestDto;
import com.tyss.strongameapp.dto.RazorpayResponse;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal
 * Payment controller class is to control payment activities.
 *
 */
@Slf4j
@CrossOrigin(origins = {"capacitor://localhost","ionic://localhost","http://localhost","http://localhost:8080","http://localhost:8100","https://strongame.web.app"})
@RestController
public class PaymentController {
	
	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private PaymentService paymentService;
	
	
	/**
	 * This method is used to generate order id.
	 * @param paymentDto
	 * @return ResponseEntity<ResponseDto>
	 */
	@PostMapping("/getorderid")
	public ResponseEntity<ResponseDto> getOrderId(@RequestBody PaymentRequestDto paymentDto ) {

		String id = paymentService.getOrderId(paymentDto.getAmount(), paymentDto.getCurrency());
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (id == null) {
			log.error("failed to generate order id");
			responseDTO.setError(true);
			responseDTO.setData("failed to generate order id");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("order id generated successfully");
			responseDTO.setError(false);
			responseDTO.setData(id);
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
	}//End of get order id method
	
	/**
	 * This method is used to verify the signature.
	 * @param data
	 * @return ResponseEntity<ResponseDto>
	 */
	@PostMapping("/verifysignature")
	public ResponseEntity<ResponseDto> verifySignature(@RequestBody RazorpayResponse data ) {

		boolean flag = paymentService.verifySignature(data);
		ResponseDto responseDTO = new ResponseDto();

		// create and return ResponseEntity object
		if (!flag) {
			log.debug("payment unsuccessfull");
			responseDTO.setError(true);
			responseDTO.setData("payment unsuccessfull");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.error("payment unsuccessfull");
			responseDTO.setError(false);
			responseDTO.setData("payment successfull");
			return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.OK);
		}
		
	}//End of verify signature method
	
}//End of Payment Controller class.

