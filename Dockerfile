FROM nginx:stable
COPY web/war /usr/share/nginx/html/notes
RUN cp /usr/share/nginx/html/notes-moodle.html /usr/share/nginx/html/index.html
