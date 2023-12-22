package com.learn;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ReadJsonUsingJsonpathApplication implements ApplicationRunner {
    @Value("classpath:mock-data/input.json")
    Resource resourceFile;

    public static void main(String[] args) {
        SpringApplication.run(ReadJsonUsingJsonpathApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //read json file content.
        String data = resourceFile.getContentAsString(StandardCharsets.UTF_8);
		//extract content from json file.
        String firstName = JsonPath.read(data, "$.firstName");
        System.out.println("firstName : " + firstName);
        String lastName = JsonPath.read(data, "$.lastName");
        System.out.println("lastName : " + lastName);
        Integer age = JsonPath.read(data, "$.age");
        System.out.println("age : " + age);
        String streetAddress = JsonPath.read(data, "$.address.streetAddress");
        System.out.println("streetAddress : " + streetAddress);
        String city = JsonPath.read(data, "$.address.city");
        System.out.println("city : " + city);
        String postalCode = JsonPath.read(data, "$.address.postalCode");
        System.out.println("postalCode : " + postalCode);

		// Method-1 to read list
		method1ToReadListData(data);
		
        // Method-2 to read list
		method2ToReadListData(data);
    }

	private static void method2ToReadListData(String data) {
		List<Object> locations = JsonPath.read(data, "$.phoneNumbers[*]");
		System.out.println("phoneNumbers start from here : ");
		for (Object item : locations) {
			Map<String, Object> map = (Map<String, Object>) item;
			System.out.println(map.get("type"));
			System.out.println(map.get("number"));
		}
	}

	private static void method1ToReadListData(String data) {
		List<Map<String, Object>> method1 = JsonPath.parse(data).read("$.phoneNumbers[*]");
		for (Map<String, Object> map1 : method1) {
			System.out.println(map1.get("type"));
			System.out.println(map1.get("number"));
		}
	}
}
