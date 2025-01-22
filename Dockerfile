FROM python:3.11-slim

RUN apt-get update \ 
    && apt-get install -y curl \
    && apt-get install -y git \
    && apt-get install -y build-essential

#using homebrew because it is the easiest way to have Java 11
RUN NONINTERACTIVE=1 /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
ENV PATH="/home/linuxbrew/.linuxbrew/bin::${PATH}"

RUN brew install openjdk@11
ENV PATH="/home/linuxbrew/.linuxbrew/opt/openjdk@11/bin:${PATH}"

RUN brew install cpanminus

RUN git clone https://github.com/rjust/defects4j

WORKDIR /defects4j
RUN  cpanm --installdeps . && ./init.sh
ENV PATH="/defects4j/framework/bin:${PATH}"

WORKDIR /
RUN git clone https://github.com/melegati/ThinkRepair.git

WORKDIR /thinkrepair
RUN pip install -r requirements.txt

CMD ["/bin/sh","-c","while sleep 1000; do :; done"]