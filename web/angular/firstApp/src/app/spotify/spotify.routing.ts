import { RouterModule } from "@angular/router";
import { SpotifyComponent } from "./spotify.component";
import { ArtistComponent } from "./artist.component";
import { AuthGuard } from "app/auth-guard.service";

export const spotifyRouting = RouterModule.forChild([
    { path: 'spotify', component: SpotifyComponent, canActivate: [AuthGuard] },
    { path: 'spotify/artist/:name', component: ArtistComponent, canActivate: [AuthGuard] }
]);