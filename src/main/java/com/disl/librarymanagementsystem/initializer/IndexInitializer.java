package com.disl.librarymanagementsystem.initializer;

import com.disl.librarymanagementsystem.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class IndexInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final IndexService indexService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        indexService.initializeIndex();
    }
}
