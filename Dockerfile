FROM python:3.11-slim

RUN apt-get update \ 
    && apt-get install -y curl git build-essential

#using homebrew because it is the easiest way to have Java 11
RUN useradd -m -s /bin/bash linuxbrew && \
    echo 'linuxbrew ALL=(ALL) NOPASSWD:ALL' >>/etc/sudoers
USER linuxbrew
ENV NONINTERACTIVE=1
RUN bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

ENV PATH="/home/linuxbrew/.linuxbrew/bin::${PATH}"

RUN brew install openjdk@11
ENV PATH="/home/linuxbrew/.linuxbrew/opt/openjdk@11/bin:${PATH}"

RUN brew install cpanminus

USER root
RUN git clone https://github.com/rjust/defects4j

WORKDIR /defects4j
RUN  cpanm --installdeps . && ./init.sh
ENV PATH="/defects4j/framework/bin:${PATH}"

WORKDIR /
RUN git clone https://github.com/melegati/ThinkRepair.git

WORKDIR /ThinkRepair
RUN pip install -r requirements.txt

CMD ["/bin/sh","-c","while sleep 1000; do :; done"]