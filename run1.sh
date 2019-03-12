cd frontend
rm -r node_modules/
npm install
STATUS=$?
if [ $STATUS -eq 0 ]; then
ng serve --o
fi
