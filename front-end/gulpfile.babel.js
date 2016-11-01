import gulp from 'gulp';
import concat from 'gulp-concat';
import uglify from 'gulp-uglify';
import sourcemaps from 'gulp-sourcemaps';
import notify from 'gulp-notify';
import minCss from 'gulp-clean-css';
import babelify from 'babelify';
import browserify from 'browserify';
import watchify from 'watchify';
import source from 'vinyl-source-stream';
import buffer from 'vinyl-buffer';
import autoprefixer from 'gulp-autoprefixer';
import browserSync, { reload } from 'browser-sync';

const paths = {
  srcJsx: 'app/index.js',
  srcCommonStyle: 'app/assets/style/common.css',
  srcSingleStyle: 'app/js/**/*.css',
  srcImages: 'app/assets/images',
  distJs: 'dist/js',
  distStyle: 'dist/assets/style',
  distImages: 'dist/assets/images',
  bundle: 'bundle.js'
};

const customOpts = {
  entries: [paths.srcJsx],
  debug: true
};

const opts = Object.assign({}, watchify.args, customOpts);

gulp.task('browserSync', () => {
  browserSync({
    server: {
      baseDir: './'
    }
  });
});

gulp.task('watchify', () => {
  let bundler = watchify(browserify(opts));

  function rebundle() {
    return bundler.bundle()
      .on('error', notify.onError())
      .pipe(source(paths.bundle))
      .pipe(buffer())
      .pipe(sourcemaps.init({loadMaps: true}))
      .pipe(sourcemaps.write('.'))
      .pipe(gulp.dest(paths.distJs))
      .pipe(reload({stream: true}));
  }

  bundler.transform(babelify)
    .on('update', rebundle);
  return rebundle();
});

gulp.task('browserify', () => {
  browserify(paths.srcJsx, {debug: true})
    .transform(babelify)
    .bundle()
    .pipe(source(paths.bundle))
    .pipe(buffer())
    .pipe(sourcemaps.init({loadMaps: true}))
    .pipe(uglify())
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(paths.distJs));
});

gulp.task('comstyles', () => {
  return gulp.src(paths.srcCommonStyle)
    .pipe(minCss())
    .pipe(autoprefixer({
      browsers: ['not ie <= 8']
    }))
    .pipe(concat('qanda.css'))
    .pipe(gulp.dest(paths.distStyle))
    .pipe(reload({stream: true}));
});

gulp.task('sinstyles', () => {
  return gulp.src(paths.srcSingleStyle)
    .pipe(autoprefixer({
      browsers: ['not ie <= 8']
    }))
    .pipe(minCss())
    .pipe(reload({stream: true}));
});

gulp.task('html', () => {
  return gulp.src('index.html')
    .pipe(reload({stream: true}));
});

gulp.task('watchTask', () => {
  gulp.watch(paths.srcCommonStyle, ['comstyles']);
  gulp.watch(paths.srcSingleStyle, ['sinstyles']);
  gulp.watch('index.html', ['html']);
});

gulp.task('serve', ['browserSync', 'watchify', 'comstyles', 'sinstyles', 'watchTask']);

gulp.task('build', ['browserify', 'comstyles', 'sinstyles']);