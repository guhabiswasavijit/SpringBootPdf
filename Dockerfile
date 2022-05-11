FROM dockette/jdk8:latest
LABEL maintainer="Avijit GuhaBiswas <guhabiswas.avijit@gmail.com>"
LABEL name="admin/spring-boot-jwt"

RUN apk add --no-cache bash wget openssh-server openssh-client vim sudo curl tar openrc\
    && adduser --disabled-password --home /home/admin --shell /bin/bash admin\
	&& echo 'admin ALL=(ALL) NOPASSWD:ALL' >>/etc/sudoers
	
USER root
RUN echo "root:root" | chpasswd
RUN mkdir -p /opt/JavaApps
WORKDIR /opt/JavaApps
ADD target/spring-boot-generate-pdf-1.0.0.jar spring-boot-generate-pdf.jar

ENTRYPOINT ["java","-jar","spring-boot-generate-pdf.jar"]