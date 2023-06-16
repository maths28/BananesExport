package fr.mb.bananesexport.order;

import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;

import java.util.Comparator;
import java.util.Optional;

public class ClassOrderTest implements ClassOrderer {
    @Override
    public void orderClasses(ClassOrdererContext classOrdererContext) {
        classOrdererContext.getClassDescriptors().sort(Comparator.comparingInt(ClassOrderTest::getOrder));
    }

    private static int getOrder(ClassDescriptor classDescriptor) {
        Optional<ClassOrder> classOrder = classDescriptor.findAnnotation(ClassOrder.class);
        return classOrder.map(ClassOrder::value).orElse(-1);
    }
}
