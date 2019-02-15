import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'calisan',
                loadChildren: './calisan/calisan.module#DefterTakipCalisanModule'
            },
            {
                path: 'ekip',
                loadChildren: './ekip/ekip.module#DefterTakipEkipModule'
            },
            {
                path: 'malzeme-takip',
                loadChildren: './malzeme-takip/malzeme-takip.module#DefterTakipMalzemeTakipModule'
            },
            {
                path: 'unvan',
                loadChildren: './unvan/unvan.module#DefterTakipUnvanModule'
            },
            {
                path: 'birim',
                loadChildren: './birim/birim.module#DefterTakipBirimModule'
            },
            {
                path: 'proje-turu',
                loadChildren: './proje-turu/proje-turu.module#DefterTakipProjeTuruModule'
            },
            {
                path: 'malzeme-grubu',
                loadChildren: './malzeme-grubu/malzeme-grubu.module#DefterTakipMalzemeGrubuModule'
            },
            {
                path: 'malzeme',
                loadChildren: './malzeme/malzeme.module#DefterTakipMalzemeModule'
            },
            {
                path: 'poz',
                loadChildren: './poz/poz.module#DefterTakipPozModule'
            },
            {
                path: 'bolge',
                loadChildren: './bolge/bolge.module#DefterTakipBolgeModule'
            },
            {
                path: 'proje',
                loadChildren: './proje/proje.module#DefterTakipProjeModule'
            },
            {
                path: 'iscilik',
                loadChildren: './iscilik/iscilik.module#DefterTakipIscilikModule'
            },
            {
                path: 'iscilik-detay',
                loadChildren: './iscilik-detay/iscilik-detay.module#DefterTakipIscilikDetayModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefterTakipEntityModule {}
