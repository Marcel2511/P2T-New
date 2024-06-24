package de.dhbw.woped.process2text;

import de.dhbw.woped.process2text.service.TransformerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransformerServiceTest2 {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private TransformerService transformerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transformerService = spy(new TransformerService()); // Achtung: Hier wird ein neues Objekt erstellt
        doReturn(webClient).when(transformerService).getWebClient();
    }

    // Hier wird die Methode getWebClient hinzugefügt
    private WebClient getWebClient() {
        return this.webClient;
    }

    @Test
    void testTransform() {
        String direction = "BPMNtoPNML";
        String bpmnXml = "<xml></xml>";
        String expectedResponse = "<transformedXml></transformedXml>";

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class))).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.body(any(BodyInserters.FormInserter.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(expectedResponse));

        String actualResponse = transformerService.transform(direction, bpmnXml);

        assertEquals(expectedResponse, actualResponse);
        verify(webClient.post(), times(1)).uri(any(String.class));
        verify(requestBodyUriSpec, times(1)).body(any(BodyInserters.FormInserter.class));
        verify(requestHeadersSpec, times(1)).retrieve();
        verify(responseSpec, times(1)).bodyToMono(String.class);
    }
}
