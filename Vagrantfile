# -*- mode: ruby -*-
# vi: set ft=ruby :

K8S_DEV_BOX_NAME = "gsengun/k8s-dev-box"
K8S_DEV_BOX_VERSION = "18.02.27"

DEV_NODE_IP="172.18.44.20"

Vagrant.configure(2) do |config|

  (0..0).each do |i|
    config.vm.define "defter-takip-box" do |node|
      node.vm.box = K8S_DEV_BOX_NAME
      node.vm.box_version = K8S_DEV_BOX_VERSION

      node.vm.hostname = "defter-takip-box"
      node.vm.network "private_network", ip: "#{DEV_NODE_IP}"

      config.vm.provision "shell", inline: <<-SHELL
        sudo su
        echo "\n----- Installing Java 8 ------\n"
        add-apt-repository -y ppa:webupd8team/java
        apt-get update
        echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | sudo debconf-set-selections
        apt-get install -y oracle-java8-installer

        echo JAVA_HOME="/usr/lib/jvm/java-8-oracle" >> /etc/environment
        source /etc/environment
        echo "\n----- Java 8 Installed -------\n"
        echo "\n----- ***************** ------\n"

        echo "\n----- Installing Gradle ------\n"
        cd /tmp/
        wget https://services.gradle.org/distributions/gradle-4.6-bin.zip
        unzip gradle-4.6-bin.zip -d /usr/local
        ln -s /usr/local/gradle-4.6 gradle
        echo GRADLE_HOME="/usr/local/gradle-4.6" >> /etc/environment
        source /etc/environment
        echo "\n----- Gradle Installed -------\n"
        echo "\n----- ***************** ------\n"


        echo "\n----- Installing NodeJS ------\n"
        apt-get purge nodejs
        cd /tmp/
        wget https://nodejs.org/dist/v9.9.0/node-v9.9.0-linux-x64.tar.gz
        mkdir /usr/local/lib/nodejs
        tar -xvzf node-v9.9.0-linux-x64.tar.gz -C /usr/local/lib/nodejs
        mv /usr/local/lib/nodejs/node-v9.9.0-linux-x64 /usr/local/lib/nodejs/node-v9.9.0
        echo NODEJS_HOME="/usr/local/lib/nodejs/node-v9.9.0" >> /etc/environment
        source /etc/environment

        echo PATH=${GRADLE_HOME}/bin:${NODEJS_HOME}/bin:${PATH} >> /etc/environment
        source /etc/environment

        sudo npm install -g @angular/cli
        echo "\n----- NodeJS Installed -------\n"
        echo "\n----- ***************** ------\n"
      SHELL

      node.vm.provision "shell", inline: "echo 'cd /vagrant' >> ~/.bashrc && exit", privileged: false

      # Setup resources
      node.vm.provider "virtualbox" do |vb|
        vb.memory = "4096"
        vb.cpus = 2
      end
    end
  end
end
