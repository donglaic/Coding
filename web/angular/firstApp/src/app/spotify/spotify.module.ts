import { ReactiveFormsModule } from "@angular/forms";
import { CommonModule } from "@angular/common";
import { HttpModule } from "@angular/http";
import { RouterModule } from "@angular/router";
import { SpotifyComponent } from "./spotify.component";
import { ArtistComponent } from "./artist.component";
import { SpotifyService } from "./spotify.service";
import { NgModule } from "@angular/core";

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        HttpModule,
        RouterModule
    ],
    declarations: [
        SpotifyComponent,
        ArtistComponent
    ],
    exports: [
    ],
    providers: [
        SpotifyService
    ]
})
export class SpotifyModule {
}