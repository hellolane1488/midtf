install:
	@INSTALLATION/install_dependencies.sh
	@./gradlew build

	@mkdir -p ~/MIDTF
	@sudo mv ~/MIDTF /usr/share

	@sudo cp -r build/libs/*.jar /usr/share/MIDTF
	@sudo cp -r INSTALLATION/midtf /usr/bin/
	@sudo cp -r INSTALLATION/midtf.1 /usr/share/man/man1/
	@sudo gzip /usr/share/man/man1/midtf.1
	@sudo chmod 755 /usr/bin/midtf
	@sudo chmod 755 /usr/share/MIDTF/*.jar

clean:
	@./gradlew clean

uninstall:
	@sudo rm -rf /usr/share/MIDTF /usr/bin/midtf /usr/share/man/man1/midtf.1* ~/MIDTF