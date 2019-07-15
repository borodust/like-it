#!/bin/bash

# from https://docs.docker.com/install/linux/docker-ce/debian/#install-docker-ce
apt-get update

apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg2 \
    software-properties-common \
    ufw \
    htop

curl -fsSL https://download.docker.com/linux/debian/gpg | sudo apt-key add -

add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
   $(lsb_release -cs) \
   stable"

apt-get update
apt-get install -y docker-ce docker-ce-cli containerd.io

# for ssh, http and docker swarm
ufw allow 22/tcp
ufw allow 80/tcp
ufw allow 2376/tcp
ufw allow 2377/tcp
ufw allow 7946/tcp
ufw allow 7946/udp
ufw allow 4789/udp

# for redis
ufw allow 6379/tcp
ufw allow 16379/tcp

ufw reload
ufw --force enable

systemctl enable docker
systemctl restart docker

# Otherwise this will create latency and memory usage issues with Redis
# FIXME: need to persist that into /etc/rc.local
echo never > /sys/kernel/mm/transparent_hugepage/enabled
# Otherwise background save may fail under low memory condition.
sysctl vm.overcommit_memory=1