var gulp = require('gulp')
  , concat = require('gulp-concat')
  , browserify = require('gulp-browserify')
  , less = require('gulp-less')

gulp.task('stylesheets', function() {
  gulp.src('assets/stylesheets/main.less')
    .pipe(less({ compress: true }))
    .pipe(gulp.dest('assets/stylesheets'))
})

gulp.task('javascripts', function() {
  gulp.src('assets/javascripts/application.js')
    .pipe(browserify())
    .pipe(concat('application.pack.js'))
    .pipe(gulp.dest('assets/javascripts'))
})

gulp.task('default', function() {
  gulp.watch([
    'assets/javascripts/**',
    '!assets/javascripts/application.pack.js',
    'assets/stylesheets/*.less',
    'assets/editor.html'
  ], function(event) {
    gulp.run('stylesheets', 'javascripts')
  })
})
