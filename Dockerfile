#
# Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

FROM adoptopenjdk:11-jdk-hotspot-focal
#FROM gcr.io/distroless/java-debian10:11
RUN apt-get update && apt-get install -y --no-install-recommends \
    openssh-server \
    nano \
    vim \
    less \
    net-tools \
    iputils-ping \
    netcat \
    telnet \
    socat \
    iproute2 \
    isc-dhcp-client \
    htop
COPY target/*.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
EXPOSE 29292
ENTRYPOINT ["java","-jar","app.jar"]
