package com.example.oril_test_task.listener;

import com.example.oril_test_task.persistence.entity.BaseEntity;
import com.example.oril_test_task.util.SequenceGeneratorUtil;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class BaseEntityListener extends AbstractMongoEventListener<BaseEntity> {

    private final SequenceGeneratorUtil sequenceGenerator;

    public BaseEntityListener(SequenceGeneratorUtil sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BaseEntity> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(event.getCollectionName() + "_sequence"));
        }
    }
}
