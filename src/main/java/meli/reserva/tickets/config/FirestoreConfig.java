package meli.reserva.tickets.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Configuration
public class FirestoreConfig {

	@Value("classpath:/home/davidbeltran/Documentos/Personal/Prueba/reserva-tickets/src/main/resources/reserva-tickets.json")
	private Resource privateKey;

	@Bean
	public Firestore firestore() {

		FirebaseOptions firebaseOptions;
		try {
			firebaseOptions = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(privateKey.getInputStream())).build();
			FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
			return FirestoreClient.getFirestore(firebaseApp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
