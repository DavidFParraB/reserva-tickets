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

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class FirestoreConfig {

	@Value("${gcp.authentication.token.file.path:default}")
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
			log.error("Failed to initialize Firestore: ", e);
		}
		return null;

	}
}
