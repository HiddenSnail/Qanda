import gulp from 'gulp';
import concat from 'gulp-concat';
import uglify from 'gulp-uglify';
import sourcemaps from 'gulp-sourcemaps';
import notify from 'gulp-notify';
import minCss from 'gulp-clean-css';
import eslint from 'gulp-eslint';
import babelify from 'babelify';
import browserify from 'browserify';
import watchify from 'watchify';
import source from 'vinyl-source-stream';
import buffer from 'vinyl-buffer';
import autoprefixer from 'gulp-autoprefixer';
import browserSync, {reload} from 'browser-sync';
import del from 'del';
import runSequence from 'run-sequence';
import browserifyShim from 'browserify-shim';
import history from 'connect-history-api-fallback';

const paths = {
  srcJsx: 'app/modalLog.js',
  srcCommonStyle: 'app/assets/style/common.css',
  srcImages: 'app/assets/images/*.**',
  srcLint: 'app/**/*.js',
  distJs: 'dist/js',
  distStyle: 'dist/assets/style',
  distImages: 'dist/assets/images',
  bundle: 'bundle.js',
  indexHTML: 'index.html',
};

const customOpts = {
  entries: [paths.srcJsx],
  debug: true
};

const opts = Object.assign({}, watchify.args, customOpts);

gulp.task('browserSync', () => {
  browserSync({
    server: {
      baseDir: './',
      middleware: [history()]
    },
    port: 4001
  });
});

gulp.task('watchify', () => {
  let bundler = watchify(browserify(opts));

  function rebundle() {
    return bundler
      .bundle()
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
    .transform(browserifyShim, {
      shim: {
        "react": "global:React"
      }
    })
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
    .pipe(autoprefixer({
      browsers: ['not ie <= 8']
    }))
    .pipe(minCss())
    .pipe(concat('qanda.css'))
    .pipe(gulp.dest(paths.distStyle))
    .pipe(reload({stream: true}));
});

gulp.task('html', () => {
  return gulp.src(paths.indexHTML)
    .pipe(reload({stream: true}));
});

gulp.task('images', ()=> {
  return gulp.src(paths.srcImages)
    .pipe(gulp.dest(paths.distImages));
});

gulp.task('lint', () => {
  gulp.src(paths.srcLint)
    .pipe(eslint())
    .pipe(eslint.format());
});

gulp.task('watchTask', () => {
  gulp.watch(paths.srcCommonStyle, ['comstyles']);
  gulp.watch(paths.srcJsx, ['lint']);
  gulp.watch(paths.indexHTML, ['html']);
  gulp.watch(paths.srcImages, ['images']);
});

gulp.task('clean', () => {
  return del([
    paths.distJs,
    paths.distStyle,
    paths.distImages
  ])
});

gulp.task('serve', cb => {
  runSequence(
    'clean',
    ['browserSync','watchify','comstyles', 'lint', 'images', 'watchTask'],
    cb);
});

gulp.task('build', cb => {
  process.env.NODE_ENV = 'production';
  runSequence(
    'clean',
    ['browserify', 'comstyles', 'images'],
    cb);
});