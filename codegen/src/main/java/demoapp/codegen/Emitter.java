package demoapp.codegen;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

import org.springframework.util.Assert;

import demoapp.codegen.demoshowcases.value.ValueShowCase;
import demoapp.codegen.demoshowcases.value.ValueTypeGenTemplate;
import demoapp.codegen.demoshowcases.value.ValueTypeGenTemplate.Config;

record Emitter(ResourceFolder projectRoot) {

    void emitAll() {
        Stream.of(ValueShowCase.values())
            .forEach(this::emit);
    }

    void emit(final ValueShowCase valueShowCase) {
        var preconfig = valueShowCase.getConfigBuilder()
                .build();
        var config = valueShowCase.getConfigBuilder()
            .outputRootDir(outputDir(preconfig))
            .build();

        var generator = new ValueTypeGenTemplate(config);
        var generatedFiles = new LinkedHashSet<File>();
        generator.generate(generatedFiles::add);

        generatedFiles.forEach(genFile->{
            System.out.println("generated: %s".formatted(generatedFiles));
        });

    }

    // -- HELPER

    private File outputDir(final Config preconfig) {
        var targetFolder = projectRoot.relativeFile(
                "domain/src/main/java/" + preconfig.getJavaPackage().replace('.', '/'));
        Assert.isTrue(targetFolder.exists(), ()->"this is implemented as update only, "
                + "package folder %s is expected to already exist"
                .formatted(preconfig.getJavaPackage()));
        return targetFolder;
    }

}
