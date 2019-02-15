import { IEkip } from 'app/shared/model/ekip.model';
import { IProje } from 'app/shared/model/proje.model';
import { IIscilikDetay } from 'app/shared/model/iscilik-detay.model';

export interface IIscilik {
    id?: number;
    ad?: string;
    aciklama?: any;
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
        public aciklama?: any,
        public resimContentType?: string,
        public resim?: any,
        public ekip?: IEkip,
        public proje?: IProje,
        public isciliks?: IIscilikDetay[]
    ) {}
}
