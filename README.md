# Inshorts – Movies Database (TMDB)

Android app showing trending and now playing movies, search, and bookmarks using [The Movie Database (TMDB) API](https://developers.themoviedb.org/3/getting-started/introduction). Built with Clean Architecture, MVVM, Kotlin, Room, and Retrofit.

## Configuration

### TMDB API key

The app needs a TMDB API key for all network requests.

1. Get a key: [TMDB API Settings](https://www.themoviedb.org/settings/api) (create an account if needed).
2. Add it to **`local.properties`** in the project root (this file is gitignored and will not be committed):

   ```properties
   TMDB_API_KEY=your_api_key_here
   ```

3. Sync the project. The key is read at build time and exposed as `BuildConfig.TMDB_API_KEY` in the app.

If `TMDB_API_KEY` is missing, the value will be an empty string and API calls will fail until you add it.
