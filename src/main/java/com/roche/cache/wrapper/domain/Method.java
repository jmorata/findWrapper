package com.roche.cache.wrapper.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
@Getter
public class Method implements Comparable<Method> {

    private String fileName;

    private String line;

    private String method;

    public static class MethodBuilder {
        public MethodBuilder method(String method) {
            this.method = method.replace("(", "");
            return this;
        }
    }

    @Override
    public int compareTo(Method o) {
        return this.method.compareTo(o.method);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Method)) return false;
        Method method1 = (Method) o;
        return Objects.equals(method, method1.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method);
    }

    @Override
    public String toString() {
        return "method='" + method + '\'' +
                ", line='" + line + '\'' +
                ", fileName='" + fileName + '\'';
    }
}
