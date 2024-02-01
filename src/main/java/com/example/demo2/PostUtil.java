package com.example.demo2;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class PostUtil {

    @GetMapping("/webclient/{param}")
	public String testWebClient(
		@PathVariable String param,
		@RequestHeader HttpHeaders headers,
		@CookieValue(name = "httpclient-type", required=false, defaultValue="undefined") String httpClientType) {
		
			log.info(">>>> Cookie 'httpclient-type={}'", httpClientType);
			headers.forEach((key, value) -> {
				log.info(String.format(">>>>> Header '%s' => %s", key, value));
			});
		
        	log.info("### Received: /webclient/" + param);
		
			String msg = param + " => Working successfully !!!";
			log.info("### Sent: " + msg);
			return msg;
	}

    @GetMapping("/test")
    public Mono<String> doTest() {
		
        WebClient client = WebClient.create();
        return client.get()
            .uri("http://localhost:8080/webclient/test-create")
            .retrieve()
            .bodyToMono(String.class);
    }

	@GetMapping("/sendmessage")
    public Mono<String> sendTeamsMessage() {
		
		String url ="https://skcccorp.webhook.office.com/webhookb2/fc07f52c-feb4-4f64-b2e6-9e3b627f8742@153ce48b-dcf2-423e-9809-c2d606ebf4b4/IncomingWebhook/04d945a8fcfc42c6b4bc221745441a6d/8230543b-2eb0-4007-bcf9-bd1e0958af17";
	
		String request = "{\n" + //
				" \"@type\": \"MessageCard\",\n" + //
				" \"summary\": \"Channel Message\",\n" + //
				" \"sections\": [\n" + //
				"  {\n" + //
				"   \"activityTitle\": \"메시지테스트\",\n" + //
				"   \"facts\": [\n" + //
				"    {\n" + //
				"     \"name\": \"보내는사람\",\n" + //
				"     \"value\": \"나는 RE-MON\"\n" + //
				"    },\n" + //
				"    {\n" + //
				"     \"name\": \"메시지\",\n" + //
				"     \"value\": \"Teams 채널 채팅창에 띄우기 위한 메시지 입니다. 식사는 맛있게 하셨나요  좋은 하루 되셨나요~  건강 챙기면서~ \\n" + //
				"\\n" + //
				" Good Day~\"\n" + //
				"    }\n" + //
				"   ]\n" + //
				"  }\n" + //
				" ]\n" + //
				"}";

        WebClient client = WebClient.create();
        return client.post()
            .uri(url)
			.header("Content-Type", "application/json")
			.bodyValue(request)
            .retrieve()
            .bodyToMono(String.class);
    }

}
