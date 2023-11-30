IMAGE_NAME = recipebook_image
CONTAINER_NAME = recipebook_container

all:
	echo "Building new image $(IMAGE_NAME)"
	docker build -t $(IMAGE_NAME) .

run:
	@if [ $$(docker ps -a -q -f name=$(CONTAINER_NAME)) ]; then \
		echo "Restarting existing container $(CONTAINER_NAME)"; \
		docker start -a $(CONTAINER_NAME); \
	else \
		echo "Creating and starting new container $(CONTAINER_NAME)"; \
		docker run --name $(CONTAINER_NAME) -v $(PWD):/app $(IMAGE_NAME); \
	fi

clean:
	docker rm $$(docker ps -a -q --filter ancestor=$(IMAGE_NAME))
	docker rmi $(IMAGE_NAME)

re:	clean all

.PHONY: all down clean re


