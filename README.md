#Use case

I upgraded my spring boot application from 1.2.x to 1.5.x. In this verstion `spring-boot-starter-data-jpa` relies on Hibernate 5 and is not compatible with Hibernate 4.

I'm using java version "1.8.0_51"

# Problem

When running/debugging in IDE (Eclipse in this case) all classed are properly accessible in the class path. `annotatedPackages` has value of `[com.example.demo.payload]` in this case.

See `org.hibernate.boot.model.source.internal.annotations.AnnotationMetadataSourceProcessorImpl`~184

```java
		for ( String annotatedPackage : annotatedPackages ) {
			AnnotationBinder.bindPackage( annotatedPackage, rootMetadataBuildingContext );
		}
```

When running/debugging compiled jar  variable `annotatedPackages` has value of `[BOOT-INF.classes.com.example.demo.payload]` which is not matching the signature of package `com/example/demo/payload/package-info`

Prefix `BOOT-INF.classes` is expected per [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html#executable-jar-jar-file-structure)

# Steps to reproduce 
Run project from Eclipse - project boots up and works as expected.

Compile jar

```
./gradlew build -x test
```

and run it

```
java -jar build/libs/spring-boot-jpa-issue-01-0.0.1-SNAPSHOT.jar
```

**Expected result** - project boots up and works as expected.

**Actuar result** - boot fails with exception 

```
2017-10-18 16:12:06.578 ERROR 18449 --- [           main] o.s.boot.SpringApplication               : Application startup failed

org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dbLogsEntityManagerFactory' defined in class path resource [bootstrap.xml]: Invocation of init method failed; nested exception is java.lang.NoClassDefFoundError: BOOT-INF/classes/com/example/demo/payload/package-info (wrong name: com/example/demo/payload/package-info)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1628) ~[spring-beans-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:555) ~[spring-beans-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:483) ~[spring-beans-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306) ~[spring-beans-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230) ~[spring-beans-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302) ~[spring-beans-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197) ~[spring-beans-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1078) ~[spring-context-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:857) ~[spring-context-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:543) ~[spring-context-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.boot.context.embedded.EmbeddedWebApplicationContext.refresh(EmbeddedWebApplicationContext.java:122) ~[spring-boot-1.5.8.RELEASE.jar!/:1.5.8.RELEASE]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:693) [spring-boot-1.5.8.RELEASE.jar!/:1.5.8.RELEASE]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:360) [spring-boot-1.5.8.RELEASE.jar!/:1.5.8.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:303) [spring-boot-1.5.8.RELEASE.jar!/:1.5.8.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1118) [spring-boot-1.5.8.RELEASE.jar!/:1.5.8.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1107) [spring-boot-1.5.8.RELEASE.jar!/:1.5.8.RELEASE]
	at com.example.demo.SpringBootJpaIssue01Application.main(SpringBootJpaIssue01Application.java:24) [classes!/:na]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_51]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_51]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_51]
	at java.lang.reflect.Method.invoke(Method.java:497) ~[na:1.8.0_51]
	at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:48) [spring-boot-jpa-issue-01-0.0.1-SNAPSHOT.jar:na]
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:87) [spring-boot-jpa-issue-01-0.0.1-SNAPSHOT.jar:na]
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:50) [spring-boot-jpa-issue-01-0.0.1-SNAPSHOT.jar:na]
	at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:51) [spring-boot-jpa-issue-01-0.0.1-SNAPSHOT.jar:na]
Caused by: java.lang.NoClassDefFoundError: BOOT-INF/classes/com/example/demo/payload/package-info (wrong name: com/example/demo/payload/package-info)
	at java.lang.ClassLoader.defineClass1(Native Method) ~[na:1.8.0_51]
	at java.lang.ClassLoader.defineClass(ClassLoader.java:760) ~[na:1.8.0_51]
	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142) ~[na:1.8.0_51]
	at java.net.URLClassLoader.defineClass(URLClassLoader.java:467) ~[na:1.8.0_51]
	at java.net.URLClassLoader.access$100(URLClassLoader.java:73) ~[na:1.8.0_51]
	at java.net.URLClassLoader$1.run(URLClassLoader.java:368) ~[na:1.8.0_51]
	at java.net.URLClassLoader$1.run(URLClassLoader.java:362) ~[na:1.8.0_51]
	at java.security.AccessController.doPrivileged(Native Method) ~[na:1.8.0_51]
	at java.net.URLClassLoader.findClass(URLClassLoader.java:361) ~[na:1.8.0_51]
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424) ~[na:1.8.0_51]
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331) ~[na:1.8.0_51]
	at java.lang.ClassLoader.loadClass(ClassLoader.java:411) ~[na:1.8.0_51]
	at org.springframework.boot.loader.LaunchedURLClassLoader.loadClass(LaunchedURLClassLoader.java:94) ~[spring-boot-jpa-issue-01-0.0.1-SNAPSHOT.jar:na]
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357) ~[na:1.8.0_51]
	at java.lang.Class.forName0(Native Method) ~[na:1.8.0_51]
	at java.lang.Class.forName(Class.java:348) ~[na:1.8.0_51]
	at org.hibernate.annotations.common.util.StandardClassLoaderDelegateImpl.classForName(StandardClassLoaderDelegateImpl.java:57) ~[hibernate-commons-annotations-5.0.1.Final.jar!/:5.0.1.Final]
	at org.hibernate.boot.internal.MetadataBuilderImpl$MetadataBuildingOptionsImpl$4.classForName(MetadataBuilderImpl.java:758) ~[hibernate-core-5.0.12.Final.jar!/:5.0.12.Final]
	at org.hibernate.annotations.common.reflection.java.JavaReflectionManager.packageForName(JavaReflectionManager.java:148) ~[hibernate-commons-annotations-5.0.1.Final.jar!/:5.0.1.Final]
	at org.hibernate.cfg.AnnotationBinder.bindPackage(AnnotationBinder.java:281) ~[hibernate-core-5.0.12.Final.jar!/:5.0.12.Final]
	at org.hibernate.boot.model.source.internal.annotations.AnnotationMetadataSourceProcessorImpl.prepare(AnnotationMetadataSourceProcessorImpl.java:186) ~[hibernate-core-5.0.12.Final.jar!/:5.0.12.Final]
	at org.hibernate.boot.model.process.spi.MetadataBuildingProcess$1.prepare(MetadataBuildingProcess.java:156) ~[hibernate-core-5.0.12.Final.jar!/:5.0.12.Final]
	at org.hibernate.boot.model.process.spi.MetadataBuildingProcess.complete(MetadataBuildingProcess.java:253) ~[hibernate-core-5.0.12.Final.jar!/:5.0.12.Final]
	at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.metadata(EntityManagerFactoryBuilderImpl.java:847) ~[hibernate-entitymanager-5.0.12.Final.jar!/:5.0.12.Final]
	at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:874) ~[hibernate-entitymanager-5.0.12.Final.jar!/:5.0.12.Final]
	at org.hibernate.jpa.HibernatePersistenceProvider.createContainerEntityManagerFactory(HibernatePersistenceProvider.java:135) ~[hibernate-entitymanager-5.0.12.Final.jar!/:5.0.12.Final]
	at org.hibernate.ejb.HibernatePersistence.createContainerEntityManagerFactory(HibernatePersistence.java:50) ~[hibernate-entitymanager-5.0.12.Final.jar!/:5.0.12.Final]
	at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:353) ~[spring-orm-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:370) ~[spring-orm-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:359) ~[spring-orm-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1687) ~[spring-beans-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1624) ~[spring-beans-4.3.12.RELEASE.jar!/:4.3.12.RELEASE]
	... 24 common frames omitted
	```