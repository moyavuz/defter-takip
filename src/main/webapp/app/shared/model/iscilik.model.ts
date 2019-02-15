import { IEkip } from 'app/shared/model/ekip.model';
import { IProje } from 'app/shared/model/proje.model';
import { IIscilikDetay } from 'app/shared/model/iscilik-detay.model';

export const enum IscilikDurumu {
    BEKLIYOR = 'BEKLIYOR',
    BEKLIYOR_MALZEME = 'BEKLIYOR_MALZEME',
    BEKLIYOR_RUHSAT = 'BEKLIYOR_RUHSAT',
    CALISILIYOR = 'CALISILIYOR',
    TAMAMLANDI = 'TAMAMLANDI',
    TAMAMLANDI_EKSIK_VAR = 'TAMAMLANDI_EKSIK_VAR'
}

export interface IIscilik {
    id?: number;
    ad?: string;
    aciklama?: string;
    detay?: any;
    durumu?: IscilikDurumu;
    resimContentType?: string;
    resim?: any;
    ekip?: IEkip;
    proje?: IProje;
    isciliks?: IIscilikDetay[];
}

export class Iscilik implements IIscilik {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public detay?: any,
        public durumu?: IscilikDurumu,
        public resimContentType?: string,
        public resim?: any,
        public ekip?: IEkip,
        public proje?: IProje,
        public isciliks?: IIscilikDetay[]
    ) {}
}
