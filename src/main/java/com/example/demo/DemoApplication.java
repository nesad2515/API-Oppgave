package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void listEndpoints() {
		System.out.println("\n--- ENDPOINTS ---");
		System.out.println("GET    http://localhost:8080/api/test/array/show");
		System.out.println("PUT    http://localhost:8080/api/test/array/add/{value}");
		System.out.println("DELETE http://localhost:8080/api/test/array/remove/{index}");
		System.out.println("CREATE http://localhost:8080/api/test/array\"");
		System.out.println("----------------------------------\n");
	}

	@RestController
	@RequestMapping("api/test")
	public static class Controller {
		ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
		int numberOfUsage = 0;

		@GetMapping
		public String HelloWorld(){
			numberOfUsage++;
			return "Hello Spring Boot! usage number: " + numberOfUsage;
		}

		@GetMapping("hello/{name}")
		public String Hello(@PathVariable("name") String name, @RequestParam(value = "caps", defaultValue = "false") boolean caps){
			numberOfUsage++;
			String message = "hello " + name;
			if(caps){
				return message.toUpperCase();

			}
			if(numberOfUsage == 1){
				return message;
			}
			if(numberOfUsage == 2){
				message += "a";
			}
			if(numberOfUsage == 3){
				message += "!!";
				message.toUpperCase();
			}
			if(numberOfUsage == 4){
				message += "! you have pressed the refresh button 4 times";
				message.toUpperCase();
			}
			return message;
		}

		@PostMapping("array")
		public List<Integer> arr(){
			this.arr = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
			return arr;
		}


		@GetMapping("array/show")
		public List<Integer> arrShow(){
			return arr;
		}

		@PutMapping("array/add/{value}")
		public List<Integer> addNumber(@PathVariable int value){
			arr.add(value);
			System.out.println("HELLO");
			return arr;
		}

		@DeleteMapping("array/remove/{index}")
		public String getArray(@PathVariable int index){
			if (index < 0 || index >= arr.size()) {
				return "Error: Index " + index + " does not exist.";
			}

			int removedNumber = arr.remove(index);

			return "Succesfully removed the number: " + removedNumber + " Which was at index: " + index;
		}

	}

}
