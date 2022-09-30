package net.tiklab.pipeline;

import net.tiklab.utils.property.PropertyAndYamlSourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource(value = {"classpath:application.yaml"}, factory = PropertyAndYamlSourceFactory.class)
@EnableScheduling
@EnablePipelineDistribution
public class PipelineDistributionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PipelineDistributionApplication.class, args);
	}

}
