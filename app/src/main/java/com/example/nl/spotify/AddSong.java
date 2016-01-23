package com.example.nl.spotify;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.Spotify;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.models.ArtistSimple;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import kaaes.spotify.webapi.android.SpotifyService;
import java.util.Map;

import kaaes.spotify.webapi.android.annotations.DELETEWITHBODY;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Albums;
import kaaes.spotify.webapi.android.models.AlbumsPager;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.ArtistsCursorPager;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.CategoriesPager;
import kaaes.spotify.webapi.android.models.Category;
import kaaes.spotify.webapi.android.models.FeaturedPlaylists;
import kaaes.spotify.webapi.android.models.NewReleases;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistFollowPrivacy;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import kaaes.spotify.webapi.android.models.PlaylistsPager;
import kaaes.spotify.webapi.android.models.Result;
import kaaes.spotify.webapi.android.models.SavedAlbum;
import kaaes.spotify.webapi.android.models.SavedTrack;
import kaaes.spotify.webapi.android.models.SnapshotId;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;
import kaaes.spotify.webapi.android.models.TracksToRemove;
import kaaes.spotify.webapi.android.models.TracksToRemoveWithPosition;
import kaaes.spotify.webapi.android.models.UserPrivate;
import kaaes.spotify.webapi.android.models.UserPublic;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public class AddSong extends AppCompatActivity implements SpotifyService, View.OnClickListener {

    Track selectedTrack;
    TracksPager searchedTracks;
    EditText etAddSongSearch, etAddSongResult;
    Button bAddSongSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etAddSongSearch = (EditText) findViewById(R.id.etAddSongSearch);
        etAddSongResult = (EditText) findViewById(R.id.etAddSongResult);
        bAddSongSearch = (Button) findViewById(R.id.bAddSongSearch);

        bAddSongSearch.setOnClickListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    protected void selectTrack(Track selection) {
        selectedTrack = selection;
    }

    protected void searchForTrack(String input) {
        input = input.trim();
        String q = "";
        for (int i=0;i<input.length();i++) {
            if (input.substring(i, i+1) == " ") {
                q += "+";
            } else {
                q += input.substring(i, i+1);
            }
        }


        Player mPlayer;
        //searchedTracks = searchTracks(q);
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        spotify.searchTracks(q, new Callback<TracksPager>() {
            @Override
            public void success(TracksPager tracksPager, Response response) {
                for (Track b : tracksPager.tracks.items) {
                    String artists = "";
                    for(ArtistSimple as : b.artists){
                        artists += as.name + " ";
                    }
                    etAddSongResult.setText(b.name + " - " + artists);

                }
                searchedTracks = tracksPager;
                Log.d("Track success", "nothing here");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Track failure", error.toString());
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.bAddSongSearch:

                searchForTrack(etAddSongSearch.getText().toString());
                break;
        }
    }

    @Override
    public void getMe(Callback<UserPrivate> callback) {

    }

    @Override
    public UserPrivate getMe() {
        return null;
    }

    @Override
    public void getUser(@Path("id") String s, Callback<UserPublic> callback) {

    }

    @Override
    public UserPublic getUser(@Path("id") String s) {
        return null;
    }

    @Override
    public void getPlaylists(@Path("id") String s, @QueryMap Map<String, Object> map, Callback<Pager<PlaylistSimple>> callback) {

    }

    @Override
    public Pager<PlaylistSimple> getPlaylists(@Path("id") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getPlaylists(@Path("id") String s, Callback<Pager<PlaylistSimple>> callback) {

    }

    @Override
    public Pager<PlaylistSimple> getPlaylists(@Path("id") String s) {
        return null;
    }

    @Override
    public void getPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @QueryMap Map<String, Object> map, Callback<Playlist> callback) {

    }

    @Override
    public Playlist getPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, Callback<Playlist> callback) {

    }

    @Override
    public Playlist getPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1) {
        return null;
    }

    @Override
    public void getPlaylistTracks(@Path("user_id") String s, @Path("playlist_id") String s1, @QueryMap Map<String, Object> map, Callback<Pager<PlaylistTrack>> callback) {

    }

    @Override
    public Pager<PlaylistTrack> getPlaylistTracks(@Path("user_id") String s, @Path("playlist_id") String s1, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getPlaylistTracks(@Path("user_id") String s, @Path("playlist_id") String s1, Callback<Pager<PlaylistTrack>> callback) {

    }

    @Override
    public Pager<PlaylistTrack> getPlaylistTracks(@Path("user_id") String s, @Path("playlist_id") String s1) {
        return null;
    }

    @Override
    public void createPlaylist(@Path("user_id") String s, @Body Map<String, Object> map, Callback<Playlist> callback) {

    }

    @Override
    public Playlist createPlaylist(@Path("user_id") String s, @Body Map<String, Object> map) {
        return null;
    }

    @Override
    public SnapshotId addTracksToPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @QueryMap Map<String, Object> map, @Body Map<String, Object> map1) {
        return null;
    }

    @Override
    public void addTracksToPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @QueryMap Map<String, Object> map, @Body Map<String, Object> map1, Callback<Pager<PlaylistTrack>> callback) {

    }

    @Override
    public void removeTracksFromPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @Body TracksToRemove tracksToRemove, Callback<SnapshotId> callback) {

    }

    @Override
    public SnapshotId removeTracksFromPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @Body TracksToRemove tracksToRemove) {
        return null;
    }

    @Override
    public void removeTracksFromPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @Body TracksToRemoveWithPosition tracksToRemoveWithPosition, Callback<SnapshotId> callback) {

    }

    @Override
    public SnapshotId removeTracksFromPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @Body TracksToRemoveWithPosition tracksToRemoveWithPosition) {
        return null;
    }

    @Override
    public void replaceTracksInPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @Query("uris") String s2, Callback<Result> callback) {

    }

    @Override
    public Result replaceTracksInPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @Query("uris") String s2) {
        return null;
    }

    @Override
    public Result changePlaylistDetails(@Path("user_id") String s, @Path("playlist_id") String s1, @Body Map<String, Object> map) {
        return null;
    }

    @Override
    public void changePlaylistDetails(@Path("user_id") String s, @Path("playlist_id") String s1, @Body Map<String, Object> map, Callback<Result> callback) {

    }

    @Override
    public void followPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, Callback<Result> callback) {

    }

    @Override
    public Result followPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1) {
        return null;
    }

    @Override
    public void followPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @Body PlaylistFollowPrivacy playlistFollowPrivacy, Callback<Result> callback) {

    }

    @Override
    public Result followPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @Body PlaylistFollowPrivacy playlistFollowPrivacy) {
        return null;
    }

    @Override
    public void unfollowPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, Callback<Result> callback) {

    }

    @Override
    public Result unfollowPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1) {
        return null;
    }

    @Override
    public SnapshotId reorderPlaylistTracks(@Path("user_id") String s, @Path("playlist_id") String s1, @Body Map<String, Object> map) {
        return null;
    }

    @Override
    public void reorderPlaylistTracks(@Path("user_id") String s, @Path("playlist_id") String s1, @Body Map<String, Object> map, Callback<SnapshotId> callback) {

    }

    @Override
    public void getAlbum(@Path("id") String s, Callback<Album> callback) {

    }

    @Override
    public Album getAlbum(@Path("id") String s) {
        return null;
    }

    @Override
    public void getAlbum(@Path("id") String s, @QueryMap Map<String, Object> map, Callback<Album> callback) {

    }

    @Override
    public Album getAlbum(@Path("id") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getAlbums(@Query("ids") String s, Callback<Albums> callback) {

    }

    @Override
    public Albums getAlbums(@Query("ids") String s) {
        return null;
    }

    @Override
    public void getAlbums(@Query("ids") String s, @QueryMap Map<String, Object> map, Callback<Albums> callback) {

    }

    @Override
    public Albums getAlbums(@Query("ids") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public Pager<Track> getAlbumTracks(@Path("id") String s) {
        return null;
    }

    @Override
    public void getAlbumTracks(@Path("id") String s, Callback<Pager<Track>> callback) {

    }

    @Override
    public void getAlbumTracks(@Path("id") String s, @QueryMap Map<String, Object> map, Callback<Pager<Track>> callback) {

    }

    @Override
    public Pager<Track> getAlbumTracks(@Path("id") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getArtist(@Path("id") String s, Callback<Artist> callback) {

    }

    @Override
    public Artist getArtist(@Path("id") String s) {
        return null;
    }

    @Override
    public void getArtists(@Query("ids") String s, Callback<Artists> callback) {

    }

    @Override
    public Artists getArtists(@Query("ids") String s) {
        return null;
    }

    @Override
    public void getArtistAlbums(@Path("id") String s, Callback<Pager<Album>> callback) {

    }

    @Override
    public Pager<Album> getArtistAlbums(@Path("id") String s) {
        return null;
    }

    @Override
    public void getArtistAlbums(@Path("id") String s, @QueryMap Map<String, Object> map, Callback<Pager<Album>> callback) {

    }

    @Override
    public Pager<Album> getArtistAlbums(@Path("id") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getArtistTopTrack(@Path("id") String s, @Query("country") String s1, Callback<Tracks> callback) {

    }

    @Override
    public Tracks getArtistTopTrack(@Path("id") String s, @Query("country") String s1) {
        return null;
    }

    @Override
    public void getRelatedArtists(@Path("id") String s, Callback<Artists> callback) {

    }

    @Override
    public Artists getRelatedArtists(@Path("id") String s) {
        return null;
    }

    @Override
    public void getTrack(@Path("id") String s, Callback<Track> callback) {

    }

    @Override
    public Track getTrack(@Path("id") String s) {
        return null;
    }

    @Override
    public void getTrack(@Path("id") String s, @QueryMap Map<String, Object> map, Callback<Track> callback) {

    }

    @Override
    public Track getTrack(@Path("id") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getTracks(@Query("ids") String s, Callback<Tracks> callback) {

    }

    @Override
    public Tracks getTracks(@Query("ids") String s) {
        return null;
    }

    @Override
    public void getTracks(@Query("ids") String s, @QueryMap Map<String, Object> map, Callback<Tracks> callback) {

    }

    @Override
    public Tracks getTracks(@Query("ids") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getFeaturedPlaylists(Callback<FeaturedPlaylists> callback) {

    }

    @Override
    public FeaturedPlaylists getFeaturedPlaylists() {
        return null;
    }

    @Override
    public void getFeaturedPlaylists(@QueryMap Map<String, Object> map, Callback<FeaturedPlaylists> callback) {

    }

    @Override
    public FeaturedPlaylists getFeaturedPlaylists(@QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getNewReleases(Callback<NewReleases> callback) {

    }

    @Override
    public NewReleases getNewReleases() {
        return null;
    }

    @Override
    public void getNewReleases(@QueryMap Map<String, Object> map, Callback<NewReleases> callback) {

    }

    @Override
    public NewReleases getNewReleases(@QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getCategories(@QueryMap Map<String, Object> map, Callback<CategoriesPager> callback) {

    }

    @Override
    public CategoriesPager getCategories(@QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getCategory(@Path("category_id") String s, @QueryMap Map<String, Object> map, Callback<Category> callback) {

    }

    @Override
    public Category getCategory(@Path("category_id") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getPlaylistsForCategory(@Path("category_id") String s, @QueryMap Map<String, Object> map, Callback<PlaylistsPager> callback) {

    }

    @Override
    public PlaylistsPager getPlaylistsForCategory(@Path("category_id") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void getMySavedTracks(Callback<Pager<SavedTrack>> callback) {

    }

    @Override
    public Pager<SavedTrack> getMySavedTracks() {
        return null;
    }

    @Override
    public void getMySavedTracks(@QueryMap Map<String, Object> map, Callback<Pager<SavedTrack>> callback) {

    }

    @Override
    public Pager<SavedTrack> getMySavedTracks(@QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void containsMySavedTracks(@Query("ids") String s, Callback<boolean[]> callback) {

    }

    @Override
    public Boolean[] containsMySavedTracks(@Query("ids") String s) {
        return new Boolean[0];
    }

    @Override
    public void addToMySavedTracks(@Query("ids") String s, Callback<Object> callback) {

    }

    @Override
    public Result addToMySavedTracks(@Query("ids") String s) {
        return null;
    }

    @Override
    public void removeFromMySavedTracks(@Query("ids") String s, Callback<Object> callback) {

    }

    @Override
    public Result removeFromMySavedTracks(@Query("ids") String s) {
        return null;
    }

    @Override
    public void getMySavedAlbums(Callback<Pager<SavedAlbum>> callback) {

    }

    @Override
    public Pager<SavedAlbum> getMySavedAlbums() {
        return null;
    }

    @Override
    public void getMySavedAlbums(@QueryMap Map<String, Object> map, Callback<Pager<SavedAlbum>> callback) {

    }

    @Override
    public Pager<SavedAlbum> getMySavedAlbums(@QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void containsMySavedAlbums(@Query("ids") String s, Callback<boolean[]> callback) {

    }

    @Override
    public Boolean[] containsMySavedAlbums(@Query("ids") String s) {
        return new Boolean[0];
    }

    @Override
    public void addToMySavedAlbums(@Query("ids") String s, Callback<Object> callback) {

    }

    @Override
    public Result addToMySavedAlbums(@Query("ids") String s) {
        return null;
    }

    @Override
    public void removeFromMySavedAlbums(@Query("ids") String s, Callback<Object> callback) {

    }

    @Override
    public Result removeFromMySavedAlbums(@Query("ids") String s) {
        return null;
    }

    @Override
    public void followUsers(@Query("ids") String s, Callback<Object> callback) {

    }

    @Override
    public Result followUsers(@Query("ids") String s) {
        return null;
    }

    @Override
    public void followArtists(@Query("ids") String s, Callback<Object> callback) {

    }

    @Override
    public Result followArtists(@Query("ids") String s) {
        return null;
    }

    @Override
    public void unfollowUsers(@Query("ids") String s, Callback<Object> callback) {

    }

    @Override
    public Result unfollowUsers(@Query("ids") String s) {
        return null;
    }

    @Override
    public void unfollowArtists(@Query("ids") String s, Callback<Object> callback) {

    }

    @Override
    public Result unfollowArtists(@Query("ids") String s) {
        return null;
    }

    @Override
    public void isFollowingUsers(@Query("ids") String s, Callback<boolean[]> callback) {

    }

    @Override
    public Boolean[] isFollowingUsers(@Query("ids") String s) {
        return new Boolean[0];
    }

    @Override
    public void isFollowingArtists(@Query("ids") String s, Callback<boolean[]> callback) {

    }

    @Override
    public Boolean[] isFollowingArtists(@Query("ids") String s) {
        return new Boolean[0];
    }

    @Override
    public Boolean[] areFollowingPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @Query("ids") String s2) {
        return new Boolean[0];
    }

    @Override
    public void areFollowingPlaylist(@Path("user_id") String s, @Path("playlist_id") String s1, @Query("ids") String s2, Callback<boolean[]> callback) {

    }

    @Override
    public ArtistsCursorPager getFollowedArtists() {
        return null;
    }

    @Override
    public Result getFollowedArtists(Callback<ArtistsCursorPager> callback) {
        return null;
    }

    @Override
    public ArtistsCursorPager getFollowedArtists(@QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public Result getFollowedArtists(@QueryMap Map<String, Object> map, Callback<ArtistsCursorPager> callback) {
        return null;
    }

    @Override
    public void searchTracks(@Query("q") String s, Callback<TracksPager> callback) {

    }

    @Override
    public TracksPager searchTracks(@Query("q") String s) {
        return null;
    }

    @Override
    public void searchTracks(@Query("q") String s, @QueryMap Map<String, Object> map, Callback<TracksPager> callback) {

    }

    @Override
    public TracksPager searchTracks(@Query("q") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void searchArtists(@Query("q") String s, Callback<ArtistsPager> callback) {

    }

    @Override
    public ArtistsPager searchArtists(@Query("q") String s) {
        return null;
    }

    @Override
    public void searchArtists(@Query("q") String s, @QueryMap Map<String, Object> map, Callback<ArtistsPager> callback) {

    }

    @Override
    public ArtistsPager searchArtists(@Query("q") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void searchAlbums(@Query("q") String s, Callback<AlbumsPager> callback) {

    }

    @Override
    public AlbumsPager searchAlbums(@Query("q") String s) {
        return null;
    }

    @Override
    public void searchAlbums(@Query("q") String s, @QueryMap Map<String, Object> map, Callback<AlbumsPager> callback) {

    }

    @Override
    public AlbumsPager searchAlbums(@Query("q") String s, @QueryMap Map<String, Object> map) {
        return null;
    }

    @Override
    public void searchPlaylists(@Query("q") String s, Callback<PlaylistsPager> callback) {

    }

    @Override
    public PlaylistsPager searchPlaylists(@Query("q") String s) {
        return null;
    }

    @Override
    public void searchPlaylists(@Query("q") String s, @QueryMap Map<String, Object> map, Callback<PlaylistsPager> callback) {

    }

    @Override
    public PlaylistsPager searchPlaylists(@Query("q") String s, @QueryMap Map<String, Object> map) {
        return null;
    }


}
