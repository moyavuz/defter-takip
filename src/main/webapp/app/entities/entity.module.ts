import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'doviz-kur',
                loadChildren: './doviz-kur/doviz-kur.module#DefterTakipDovizKurModule'
            },
            {
                path: 'personel',
                loadChildren: './personel/personel.module#DefterTakipPersonelModule'
            },
            {
                path: 'ekip',
                loadChildren: './ekip/ekip.module#DefterTakipEkipModule'
            },
            {
                path: 'stok-takip',
                loadChildren: './stok-takip/stok-takip.module#DefterTakipStokTakipModule'
            },
            {
                path: 'unvan',
                loadChildren: './unvan/unvan.module#DefterTakipUnvanModule'
            },
            {
                path: 'depo',
                loadChildren: './depo/depo.module#DefterTakipDepoModule'
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
                path: 'hakedis-turu',
                loadChildren: './hakedis-turu/hakedis-turu.module#DefterTakipHakedisTuruModule'
            },
            {
                path: 'zimmet-turu',
                loadChildren: './zimmet-turu/zimmet-turu.module#DefterTakipZimmetTuruModule'
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
                path: 'poz-grubu',
                loadChildren: './poz-grubu/poz-grubu.module#DefterTakipPozGrubuModule'
            },
            {
                path: 'poz',
                loadChildren: './poz/poz.module#DefterTakipPozModule'
            },
            {
                path: 'il',
                loadChildren: './il/il.module#DefterTakipIlModule'
            },
            {
                path: 'mudurluk',
                loadChildren: './mudurluk/mudurluk.module#DefterTakipMudurlukModule'
            },
            {
                path: 'santral',
                loadChildren: './santral/santral.module#DefterTakipSantralModule'
            },
            {
                path: 'marka',
                loadChildren: './marka/marka.module#DefterTakipMarkaModule'
            },
            {
                path: 'model',
                loadChildren: './model/model.module#DefterTakipModelModule'
            },
            {
                path: 'proje',
                loadChildren: './proje/proje.module#DefterTakipProjeModule'
            },
            {
                path: 'hakedis',
                loadChildren: './hakedis/hakedis.module#DefterTakipHakedisModule'
            },
            {
                path: 'hakedis-detay',
                loadChildren: './hakedis-detay/hakedis-detay.module#DefterTakipHakedisDetayModule'
            },
            {
                path: 'hakedis-takip',
                loadChildren: './hakedis-takip/hakedis-takip.module#DefterTakipHakedisTakipModule'
            },
            {
                path: 'eskalasyon-turu',
                loadChildren: './eskalasyon-turu/eskalasyon-turu.module#DefterTakipEskalasyonTuruModule'
            },
            {
                path: 'eskalasyon',
                loadChildren: './eskalasyon/eskalasyon.module#DefterTakipEskalasyonModule'
            },
            {
                path: 'personel-zimmet',
                loadChildren: './personel-zimmet/personel-zimmet.module#DefterTakipPersonelZimmetModule'
            },
            {
                path: 'arac',
                loadChildren: './arac/arac.module#DefterTakipAracModule'
            },
            {
                path: 'personel-arac',
                loadChildren: './personel-arac/personel-arac.module#DefterTakipPersonelAracModule'
            },
            {
                path: 'personel-izin',
                loadChildren: './personel-izin/personel-izin.module#DefterTakipPersonelIzinModule'
            },
            {
                path: 'personel-odeme',
                loadChildren: './personel-odeme/personel-odeme.module#DefterTakipPersonelOdemeModule'
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
