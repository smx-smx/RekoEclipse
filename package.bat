cd ./RekoEclipse.dependencies
call mvn package
cd ../RekoEclipse.product
call mvn package
cd ..

call mvn install